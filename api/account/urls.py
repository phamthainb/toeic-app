from django.urls import path
from .views import get_profile, reset_password, set_target, signup, update_profile

urlpatterns = [
    path('get_profile/', get_profile),
    path('update_profile/', update_profile),
    path('set_target/', set_target),
    path('signup/', signup),
    path('reset_password/', reset_password),
    # path('get_test/', QuestionView.get_test),
]