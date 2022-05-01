from rest_framework.decorators import api_view, permission_classes
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated
from rest_framework.viewsets import ModelViewSet
from rest_framework.views import APIView
from account.models import CustomUser
# from .models import Target
from .serializers import CustomUserSerializer
from django.contrib.auth.hashers import make_password, check_password
from django.http import JsonResponse
from rest_framework import status


def get_user_from_username(request):
    try:
        u: CustomUser = CustomUser.objects.get(username=request.user.username)
    except:
        return False
    return u


# {
# "username": "test",
# "email": "test@gmail.com",
# "password": "12345678"
# }

@api_view(["POST"])
def signup(request):
    serializer = CustomUserSerializer(data=request.data)
    if serializer.is_valid():
        serializer.validated_data['password'] = make_password(
            serializer.validated_data['password'])
        serializer.save()

        return JsonResponse({
            'message': 'Register successful!'
        }, status=status.HTTP_201_CREATED)

    else:
        return JsonResponse({
            'message': serializer._errors,
            'status': 400,
        }, status=status.HTTP_400_BAD_REQUEST)


@api_view(["POST"])
def reset_password(request):
    o = request.data.get('old', False)
    n = request.data.get('new', False)

    if not o or not n:
        return JsonResponse({
            'message': "Invalid Field",
            'status': 400,
        }, status=status.HTTP_400_BAD_REQUEST)
    
    u = get_user_from_username(request)

    if check_password(o, u.password):
        u.password = make_password(n)
        u.save()
        return JsonResponse({
            'message': "Success",
            'status': 200,
        }, status=status.HTTP_200_OK)
    else: 
        return JsonResponse({
                'message': "Password not correct",
                'status': 400,
            }, status=status.HTTP_400_BAD_REQUEST)

# class TargetView(ModelViewSet):
#     queryset = Target.objects.all()
#     serializer_class = TargetSerializer
#     filterset_fields = ['id', 'level', 'desc']
#     permission_classes = [IsAuthenticated, ]


@api_view(["GET"])
@permission_classes([IsAuthenticated])
def get_profile(request):
    u = get_user_from_username(request=request)

    if not u:
        return Response(data={'message': "not found"}, status=400)

    return Response(data={
        "status": 'success',
        "data": CustomUserSerializer(u).data
    }, status=200)


@api_view(["PUT"])
@permission_classes([IsAuthenticated])
def update_profile(request):
    u = get_user_from_username(request=request)

    if not u:
        return Response(data={'message': "not found"}, status=400)

    birth_day = request.data.get('birth_day', False)
    if birth_day:
        u.birth_day = birth_day
    sex = request.data.get('sex', False)
    if sex:
        u.sex = sex
    phone = request.data.get('phone', False)
    if phone:
        u.phone = phone
    fullname = request.data.get('fullname', False)
    if fullname:
        u.fullname = fullname

    CustomUser.save(u)

    return Response(data={
        "status": 'success',
        "data": "Update success"
    }, status=200)


@api_view(['POST'])
def set_target(request):
    d = request.data.get('level', 0)
    if not d:
        return Response(data={'message': "Invalid level"}, status=400)
    try:
        u = get_user_from_username(request)
        u.level = d
        CustomUser.save(u)

        return Response(data={
            "status": 'success',
            "data": "Update target success"
        }, status=200)
    except:
        return Response(data={'message': "target not found"}, status=400)
