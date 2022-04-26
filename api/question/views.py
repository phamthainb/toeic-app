from rest_framework import viewsets, decorators, response
from rest_framework.permissions import IsAuthenticated
from account.views import get_profile
from question.models import Question
from question.serializers import QuestionSerializer
from question.services import gen_question


class QuestionView(viewsets.ModelViewSet):
    queryset = Question.objects.all()
    serializer_class = QuestionSerializer
    filterset_fields = ['id', 'part']
    permission_classes = [IsAuthenticated,]


    # pratice part
    @decorators.api_view(['GET'])
    def get_question(request):
        params = request.query_params
        part = params.get('part') or 1
        limmit = params.get('limmit') or 10
        return response.Response(gen_question(part=part, limit=limmit))

    # exam test
    @decorators.api_view(['GET'])
    def get_test(request):
        return response.Response(
            [
                {"part": 1, "len": 6, "question": gen_question(1, 6)},
                {"part": 2, "len": 25, "question": gen_question(2, 25)},
                {"part": 3, "len": 13, "question": gen_question(3, 13)},  # *3
                {"part": 4, "len": 10, "question": gen_question(4, 10)},  # *3
                {"part": 5, "len": 30, "question": gen_question(5, 30)},
                {"part": 6, "len": 16, "question": gen_question(6, 16)},  # *4
                {"part": 7, "len": 54, "question": gen_question(7, 54, True)}
            ]
        )
