from uuid import uuid4
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
    permission_classes = [IsAuthenticated, ]

    # pratice part

    @decorators.api_view(['GET'])
    def get_question(request):
        params = request.query_params
        part = params.get('part') or 1
        limmit = params.get('limmit') or 10
        return response.Response({
            "status": "success",
            "result": {
                "task_id": uuid4(),
                "data": gen_question(part=part, limit=limmit)
            }

        })

    # exam test
    @decorators.api_view(['GET'])
    def get_test(request):
        params = request.query_params
        part = int(params.get('part') or 0)

        task_id = params.get('task_id') or ""

        # if part == 0:
        return response.Response(
            {
                "status": "success",
                "result": {
                    "task_id": uuid4(),
                    "data":
                    gen_question(1, 6) +
                    gen_question(2, 25) +
                    gen_question(3, 13) +
                    gen_question(4, 10) +
                    gen_question(5, 30) +
                    gen_question(6, 16) +
                    gen_question(7, 54, True)
                }
            }
        )

        # # default return part 1
        # res = {"part": 1, "len": 6, "question": gen_question(1, 6)}

        # if part == 7:
        #     res = {"part": 7, "len": 54,
        #            "question": gen_question(7, 54, True)}
        # elif part == 2:
        #     res = {"part": 2, "len": 25,
        #                       "question": gen_question(2, 25)}
        # elif part == 3:
        #     res = {"part": 3, "len": 13,
        #                       "question": gen_question(3, 13)}
        # elif part == 4:
        #     res = {"part": 4, "len": 10,
        #                       "question": gen_question(4, 10)}
        # elif part == 5:
        #     res = {"part": 5, "len": 30,
        #                       "question": gen_question(5, 30)}
        # elif part == 6:
        #     res = {"part": 6, "len": 16,
        #                       "question": gen_question(6, 16)}

        # return response.Response(
        #     {
        #         "status": "success",
        #         "result": {
        #             "task_id": task_id if task_id != "" else uuid4(),
        #             "data": res
        #         }
        #     }
        # )
