# Generated by Django 4.0.4 on 2022-04-26 18:21

import django.contrib.postgres.fields
from django.db import migrations, models


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Question',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('tag', models.CharField(max_length=255)),
                ('kind', models.CharField(max_length=255, null=True)),
                ('part', models.IntegerField()),
                ('title', models.CharField(max_length=1023)),
                ('count_question', models.IntegerField()),
                ('general', models.JSONField()),
                ('content', django.contrib.postgres.fields.ArrayField(base_field=models.JSONField(), size=None)),
                ('correct_answers', django.contrib.postgres.fields.ArrayField(base_field=models.IntegerField(), size=None)),
                ('title_trans', models.JSONField()),
                ('scores', django.contrib.postgres.fields.ArrayField(base_field=models.IntegerField(), size=None)),
            ],
        ),
    ]
