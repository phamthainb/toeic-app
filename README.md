# toeic-app

# Task
- app (deadline: 16/4/2022)
    - Login: login, login 1, login 2, 32, 26 => Duyen
    - Home: 1, 9, 30, 11, 29 => Huy
    - (Part 1, 2, 6): 2, 3, 10, 7, 31 => Ngan
    - (Part: 3, 4, 5, 7): 4, 8, 5, 12, 25 => Thai

- api: Django + Postgre: Restfull API
    - [x] auth (sign-up, sign-in), 
    - [x] profile(user name, email, birthday)
    - [x] set target
    - [x] reset password
    - [x] part 1, 2, 3, 4, 5, 6, 7 (learn)
    - [x] full-test (try exam)
    - [x] base (contructor, database, models, router path)

- tài liệu 1
    - Kiến trúc tổng quan (các khối client/server/api nếu có)
    - Biểu đồ Usercase tổng quan
    - Usercase chi tiết
    - Biểu đồ lớp
    - Biểu đồ tuần tự
    - Sơ đồ thực thể quan hệ (ER)

- tài liệu - docx~
    - Duyên
        - singin
        - signup
        - logout
        - select target
        - update personal information 

    - Huy
        - practise
        - select part
        - answer
        - view list status
        - result page (time, true rate + list status)

    - Ngân
        - test
        - select test
        - answer
        - view result page (26)
        - history

- ghép api
    - Duyên
        - login
        - signin
        - signup (gg, bình thương)
        - target
    - Huy
        - get, put profile
        - change password
        - get history, all question (local)
    - Thái + Ngân
        - question
