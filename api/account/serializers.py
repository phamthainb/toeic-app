from dataclasses import field
from rest_framework import serializers
from .models import CustomUser

class CustomUserSerializer(serializers.ModelSerializer):
    class Meta:
        model = CustomUser
        fields = ['email', 'password', 'id', 'username', 'birth_day', 'level', 'date_joined', 'phone', 'fullname', 'sex']
        extra_kwargs = {'password': {'write_only': True}}
        
    
# class TargetSerializer(serializers.ModelSerializer):
#     class Meta:
#         model = Target
#         fields = ['id', 'level', 'desc']