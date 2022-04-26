

from django.urls import path
from question.views import QuestionView

urlpatterns = [
    path('get_question/', QuestionView.get_question),
    path('get_test/', QuestionView.get_test),
]
