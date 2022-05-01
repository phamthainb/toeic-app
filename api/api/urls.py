"""api URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include
from rest_framework import routers
import question.views
import question.urls
import account.urls
import account.views
from django.urls import re_path
from drf_yasg.views import get_schema_view
from drf_yasg import openapi

from rest_framework_simplejwt.views import (
    TokenObtainPairView,
    TokenRefreshView,
)

schema_view = get_schema_view(
    openapi.Info(
        title="Toeic App API",
        default_version='v1',
        description="API Description",
        terms_of_service="https://www.google.com/policies/terms/",
        license=openapi.License(name="D18 Team 2 PTIT"),
    ),
    public=True,
    # permission_classes=[permissions.AllowAny],
)


router = routers.DefaultRouter()
router.register('question', question.views.QuestionView)
# router.register('target', account.views.TargetView)

urlpatterns = [
    re_path(r'^swagger(?P<format>\.json|\.yaml)$',
            schema_view.without_ui(cache_timeout=0), name='schema-json'),
    re_path(r'^swagger/$', schema_view.with_ui('swagger',
            cache_timeout=0), name='schema-swagger-ui'),
    re_path(r'^redoc/$', schema_view.with_ui('redoc',
            cache_timeout=0), name='schema-redoc'),

    path('admin/', admin.site.urls),
    path('pratice/', include(question.urls)),
    path('account/', include(account.urls)),
    path('', include(router.urls)),

    re_path(r'^account/login/$', TokenObtainPairView.as_view(),
            name='token_obtain_pair'),
    re_path(r'^account/refresh/$',
            TokenRefreshView.as_view(), name='token_refresh'),

    # path('auth/', include('djoser.urls')),
    # path('auth/', include('djoser.urls.jwt')),
    # path('auth/', include('djoser.social.urls')),
]
