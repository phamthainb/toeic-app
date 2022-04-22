from dataclasses import fields
from rest_framework import serializers
from .models import Question


class QuestionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Question
        fields = (
            "id",
            "tag",
            "part",
            "kind",
            "title",
            "count_question",
            "general",
            "content",
            "correct_answers",
            "title_trans",
            "scores",
        )
        
