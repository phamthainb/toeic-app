//clean_data.js
/// <reference path="./db.js" />
// @ts-check

var axios = require('axios');
const { createWriteStream, existsSync, mkdirSync } = require("fs");
const path = require('path');
const { pool } = require("./db");

var reg = /https:\/\/migiitoeic\.eupgroup\.net\/(.*?)(?=")|$/gi;

(async function () {
    console.log("Start...");
    let data = await pool.query(
        "select * from data"
    )
    let rows = data?.rows;
    rows.forEach(async (value, index) => {
        console.log("Loading ... " + value.id);
        let links = value.data.match(reg);
        // let result = false;

        try {
            if (links.length > 0) {
                links.forEach(async (k) => {
                    let $k = k;
                    if ($k) {
                        if ($k[$k.length - 1] === '/' || $k[$k.length - 1] === '\\') { $k = $k.slice(0, -1); }
                        let save_to = $k.split("https://migiitoeic.eupgroup.net/")[1];
                        await downloadFile($k, save_to)
                        // .then(() => { result = true })
                        // .catch(() => { result = false })
                        // .finally(() => {
                        //     console.log("down file done "+ value.id + " " + result +" >> " + $k);
                        // })
                    }
                })
            }
            const new_data = (value.data).replaceAll('https://migiitoeic.eupgroup.net/', "")
            // if(result) {
            await pool.query(
                `UPDATE data SET clean = 1, data_clean = $1 where id = $2`, [new_data, value.id]
            );
            console.log("Done " + value.id);
            // }
        } catch (error) {
            console.log(`Error in ${value.id}`);
            console.log(error);
        }
    })
    console.log("End.");
})()

async function downloadFile(fileUrl, outputLocationPath) {
    ensureDirectoryExistence(outputLocationPath);

    if (existsSync(outputLocationPath)) {
        return true;
    }
    const writer = createWriteStream(outputLocationPath);

    // @ts-ignore
    await axios({
        method: 'get',
        url: fileUrl,
        responseType: 'stream',
    }).then(response => {
        response.data.pipe(writer);
        writer.on('error', err => {
            writer.close();
            return false;
        });
    }).catch(err => {
        console.log("err " + fileUrl);
        console.log(JSON.stringify(err));
    })
    return true;
}

function ensureDirectoryExistence(filePath) {
    var dirname = path.dirname(filePath);
    if (existsSync(dirname)) {
        return true;
    }
    ensureDirectoryExistence(dirname);
    mkdirSync(dirname);
}