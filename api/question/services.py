import random

from question.models import Question


def gen_question(part, limit, sum_child=False):
    count = []
    total = 0
    list_quest = Question.objects.filter(part=part).values()
    len_max = len(list_quest) - 1
    result = []
    # print("len " + str(part) + " => " + str(len_max))
    if(len_max >= 0):
        while(len(count) + 1 <= int(limit)):
            r = random.randint(0, len_max)
            time_check = 0

            while(True):
                if time_check >= 10:
                    break

                if r in count:
                    r = random.randint(0, len_max)
                    time_check += 1
                    continue
                else:
                    break

            # print("r >> "+ str(r))
            q: Question = list_quest[r]
            result.append({
                "id": q['id'],
                "part": q['part'],
                "correct_answers": q['correct_answers'],
                "count_question": q['count_question'],
            })
            
            count.append(r)
            total += q['count_question'] or 1

            if sum_child and total >= limit:
                break

    return result
