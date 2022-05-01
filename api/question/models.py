from django.db import models
from django.contrib.postgres.fields import ArrayField
# Create your models here.


class Question(models.Model):
    tag = models.CharField(max_length=255)
    kind = models.CharField(max_length=255, null=True)
    part=models.IntegerField()
    title = models.CharField(max_length=1023)
    count_question = models.IntegerField()
    general = models.JSONField()
    content = ArrayField(models.JSONField())
    correct_answers = ArrayField(models.IntegerField())
    title_trans = models.JSONField()
    scores = ArrayField(models.IntegerField())

    def __str__(self):
        return self.title
