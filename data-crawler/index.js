var axios = require('axios');
const { pool } = require("./db");
var randomUseragent = require('random-useragent');

var kind = {
    "1": "picture description",
    "2": "question - response",
    "3": "short conversations",
    "4": "short talks",
    "5": "incomplete sentences",
    "6": "text completion",
    "7": "reading comprehension",
};

var kind_id = {
    "picture description": "1",
    "question - response": "2",
    "short conversations": "3",
    "short talks": "4",
    "incomplete sentences": "5",
    "text completion": "6",
    "reading comprehension": "7",
};

var n_random = (min, max) => Math.floor(Math.random() * (max - min + 1)) + min;

var sleep = ms => new Promise(r => setTimeout(r, ms));

/// get_by_question
var get_questions = (part, limit) => {
    var config = {
        method: 'get',
        url: `https://toeic.migii.net/resapi/Questions/practice?kind=${kind[part]}&limit=${limit}`,
        headers: { 'User-Agent': randomUseragent.getRandomData((ua) => ua.osName === "Android" || ua.osName === "iOS") }
    };

    axios(config)
        .then(function (response) {
            var list = response.data.Questions.Questions;
            //id, data, part
            list.forEach(async (i, j) => {
                try {
                    await pool.query(
                        "INSERT INTO data (id, part, data) VALUES ($1, $2, $3)",
                        [i.id, part, JSON.stringify(i)]
                    );
                    console.log(j + 1, " Insert successs id: ", i.id);
                } catch (error) {
                    console.log(j + 1, " Insert error id: ", i.id);
                }
            })
        })
        .catch(function (error) {
            console.log("call api error", JSON.stringify(error));
        });
}

var get_by_question = async () => {
    let i = 1;
    while (i < 5000) {
        var sl = n_random(40, 70), p = i % 7 ? i % 7 : 7, lm = n_random(10, 30);

        console.log("start ", i, new Date().toISOString());
        console.log(`wait ${sl}s`);
        console.log(`part: ${p}, limit: ${lm}`);
        get_questions(p, lm);
        await sleep(sl * 1000);
        console.log("end ", i, new Date().toISOString());
        i++;
        console.log("-----------------------");
    }
    console.log("crawler end.");
}

get_by_question();

/// get_by_exam
var get_exam = (id) => {
    var config = {
        method: 'get',
        url: `https://toeic.migii.net/resapi/Questions/testExam?id=${id}`,
        headers: { 'User-Agent': randomUseragent.getRandomData((ua) => ua.osName === "Android" || ua.osName === "iOS") }
    };

    axios(config)
        .then(function (response) {
            var list = response.data.Questions.parts;

            // content
            var list_content = [];
            list.map((i) => { i.content.map(j => { list_content.push(j) }) });

            // console.log("list_content", list_content.length, typeof list)
            //Listening, Reading
            list_content.forEach(async (list_content_i, list_content_j) => {
                var k = kind_id[list_content_i.kind];
                var list_question = list_content_i.Questions;

                // console.log("list_question", list_question);
                // console.log("list_content_i", list_content_i);

                // content
                list_question.forEach(async (list_question_i, list_question_j) => {
                    try {
                        await pool.query(
                            "INSERT INTO data (id, part, data) VALUES ($1, $2, $3)",
                            [list_question_i.id, k, JSON.stringify(list_question_i)]
                        );
                        console.log(list_question_j + 1, " Insert successs id: ", list_question_i.id);
                    } catch (error) {
                        console.log(list_question_j + 1, " Insert error id: ", list_question_i.id);
                    }
                })
            })
        })
        .catch(function (error) {
            console.log("call api error", error);
        });
}

var get_by_exam = async () => {
    let i = 1;
    while (i < 31) {
        var sl = 3;

        console.log("start ", i, new Date().toISOString());
        console.log(`wait ${sl}s`);
        get_exam(i);
        await sleep(sl * 1000);
        console.log("end ", i, new Date().toISOString());
        i++;
        console.log("-----------------------");
    }
    console.log("crawler end.");
}

get_by_exam();


module.exports = {
    n_random, sleep
}