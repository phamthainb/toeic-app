

from django.urls import path

from question.views import get_question, get_test


urlpatterns = [
    path('get_question/', get_question),
    path('get_test/', get_test),
]