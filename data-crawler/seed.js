//clean_data.js
// @ts-check

const axios = require('axios').default;
const { pool_app, pool_data } = require("./db");
var fs = require('fs');

var sleep = ms => new Promise(r => setTimeout(r, ms));
(async function () {
    console.log("Start...");
    let data = await pool_data.query(
        "select * from data"
    )
    let rows = data?.rows;
    var c = [], e = [];

    for (let index = 0; index < rows.length; index++) {
        const value = rows[index];
        const {
            id,
            tag,
            part,
            kind,
            title,
            count_question,
            general,
            content,
            correct_answers,
            title_trans,
            scores,
        } = JSON.parse(value.data);

        const datax = JSON.stringify({
            tag: tag,
            part: value.part,
            kind: kind,
            title: title,
            count_question: count_question,
            general: general,
            content: content,
            correct_answers: correct_answers,
            title_trans: typeof title_trans === 'string' ? JSON.parse(title_trans) : title_trans,
            scores: scores ?? Array(count_question).fill(1),
        });
        // console.log("datax", part, scores);

        try {

            await axios({
                method: "POST", url: 'http://localhost:8000/question/',
                data: datax,
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    'Authorization': 'Basic PEJhc2ljIEF1dGggVXNlcm5hbWU+OjxCYXNpYyBBdXRoIFBhc3N3b3JkPg=='
                },
            })

            console.log("response");
            c.push(value.id)
        } catch (error) {
            console.log("error", error);
            e.push(value.id)
        }

        await sleep(15);

        console.log(`${c.length}/${rows.length}`, e.length);
    }

    fs.writeFile('seed.json', JSON.stringify({ c, e }), 'utf8', (e) => {
        console.log(e);
    });

    console.log("End.");
})()


let a = {
    "id": 297,
    "tag": "read",
    "kind": "reading comprehension",
    "general": {
        "audio": "",
        "txt_read": "<p><img alt=\"\" src=\"https://migiitoeic.eupgroup.net/uploads/image_ETS/4957ae18ca2d91773ba835dd1aab792d.png\" style=\"width:100%\" /></p>",
        "txt_read_vn": "<p>THƯ B&Aacute;O</p><p>Đến: Nh&acirc;n vi&ecirc;n Ph&ograve;ng th&iacute; nghiệm Westhauser<br />Từ: Quản l&yacute; khu vực<br />Ng&agrave;y: Ng&agrave;y 22 th&aacute;ng 3<br />Về: Đồ uống giờ giảo lao</p><p>Bắt đầu kể từ b&acirc;y giờ ngay lập tức, c&aacute;c nh&acirc;n vi&ecirc;n sẽ được y&ecirc;u cầu đ&oacute;ng g&oacute;p v&agrave;o chi ph&iacute; cho c&aacute;c loại đồ uống n&oacute;ng c&oacute; sẵn trong căn bếp mini của ch&uacute;ng ta. Đối với mỗi cốc c&agrave; ph&ecirc; hoặc tr&agrave; bạn r&oacute;t cho m&igrave;nh, ch&uacute;ng t&ocirc;i y&ecirc;u cầu bạn bỏ ra 50 xu. Xin lưu &yacute; rằng số tiền n&agrave;y vẫn c&ograve;n &iacute;t hơn những g&igrave; bạn sẽ trả tại qu&aacute;n c&agrave; ph&ecirc; cạnh t&ograve;a nh&agrave; của ch&uacute;ng ta.<br />Mỗi lần bạn chuẩn bị đồ uống, chỉ cần đặt tiền của bạn v&agrave;o c&aacute;i ca được đ&aacute;nh dấu tr&ecirc;n quầy kế bồn rửa ch&eacute;n. Cũng giống như l&uacute;c trước, đa dạng những l&ocirc; c&agrave; ph&ecirc; v&agrave; tr&agrave; chất lượng cao sẽ được cung cấp v&agrave; sẵn c&oacute; để d&ugrave;ng, v&agrave; c&aacute;c sản phẩm n&agrave;y sẽ được cung cấp th&ecirc;m v&agrave;o hằng th&aacute;ng. Ch&uacute;ng t&ocirc;i cảm k&iacute;ch với sự th&ocirc;ng cảm của c&aacute;c bạn để mọi người c&oacute; thể tiếp tục thưởng thức đồ uống n&oacute;ng ở nơi thuận tiện trong suốt ng&agrave;y l&agrave;m việc.</p>",
        "image": "",
        "txt_audio": "",
        "txt_audio_vn": "",
        "txt_read_trans": {
            "vi": "<p>THƯ B&Aacute;O</p><p>Đến: Nh&acirc;n vi&ecirc;n Ph&ograve;ng th&iacute; nghiệm Westhauser<br />Từ: Quản l&yacute; khu vực<br />Ng&agrave;y: Ng&agrave;y 22 th&aacute;ng 3<br />Về: Đồ uống giờ giảo lao</p><p>Bắt đầu kể từ b&acirc;y giờ ngay lập tức, c&aacute;c nh&acirc;n vi&ecirc;n sẽ được y&ecirc;u cầu đ&oacute;ng g&oacute;p v&agrave;o chi ph&iacute; cho c&aacute;c loại đồ uống n&oacute;ng c&oacute; sẵn trong căn bếp mini của ch&uacute;ng ta. Đối với mỗi cốc c&agrave; ph&ecirc; hoặc tr&agrave; bạn r&oacute;t cho m&igrave;nh, ch&uacute;ng t&ocirc;i y&ecirc;u cầu bạn bỏ ra 50 xu. Xin lưu &yacute; rằng số tiền n&agrave;y vẫn c&ograve;n &iacute;t hơn những g&igrave; bạn sẽ trả tại qu&aacute;n c&agrave; ph&ecirc; cạnh t&ograve;a nh&agrave; của ch&uacute;ng ta.<br />Mỗi lần bạn chuẩn bị đồ uống, chỉ cần đặt tiền của bạn v&agrave;o c&aacute;i ca được đ&aacute;nh dấu tr&ecirc;n quầy kế bồn rửa ch&eacute;n. Cũng giống như l&uacute;c trước, đa dạng những l&ocirc; c&agrave; ph&ecirc; v&agrave; tr&agrave; chất lượng cao sẽ được cung cấp v&agrave; sẵn c&oacute; để d&ugrave;ng, v&agrave; c&aacute;c sản phẩm n&agrave;y sẽ được cung cấp th&ecirc;m v&agrave;o hằng th&aacute;ng. Ch&uacute;ng t&ocirc;i cảm k&iacute;ch với sự th&ocirc;ng cảm của c&aacute;c bạn để mọi người c&oacute; thể tiếp tục thưởng thức đồ uống n&oacute;ng ở nơi thuận tiện trong suốt ng&agrave;y l&agrave;m việc.</p>",
            "ja": "<p><img alt=\"\" src=\"https://migiitoeic.eupgroup.net/uploads/image_ETS/4957ae18ca2d91773ba835dd1aab792d.png\" style=\"width:100%\" /></p>",
            "ko": "<p><img alt=\"\" src=\"https://migiitoeic.eupgroup.net/uploads/image_ETS/4957ae18ca2d91773ba835dd1aab792d.png\" style=\"width:100%\" /></p>",
            "zh-CN": "<p><img alt=\"\" src=\"https://migiitoeic.eupgroup.net/uploads/image_ETS/4957ae18ca2d91773ba835dd1aab792d.png\" style=\"width:100%\" /></p>",
            "fr": "<p><img alt=\"\" src=\"https://migiitoeic.eupgroup.net/uploads/image_ETS/4957ae18ca2d91773ba835dd1aab792d.png\" style=\"width:100%\" /></p>",
            "zh-TW": "<p><img alt=\"\" src=\"https://migiitoeic.eupgroup.net/uploads/image_ETS/4957ae18ca2d91773ba835dd1aab792d.png\" style=\"width:100%\" /></p>"
        }
    },
    "content": [
        {
            "question": "What is the purpose of the memo?",
            "answers": [
                "To explain a new policy",
                "To discourage long breaks",
                "To provide a budget update",
                "To address staff complaints"
            ],
            "correctAnswer": 0,
            "image": "",
            "explainAll": {
                "vn": "<p>Mục đ&iacute;ch của thư b&aacute;o l&agrave; g&igrave;?</p><p>A. Để giải th&iacute;ch một ch&iacute;nh s&aacute;ch mới</p><p>B. Khuyến kh&iacute;ch kh&ocirc;ng nghỉ l&acirc;u d&agrave;i</p><p>C. Cung cấp bảng cập nhật ng&acirc;n s&aacute;ch</p><p>D. Giải quyết khiếu nại của nh&acirc;n vi&ecirc;n</p>###<p>Th&ocirc;ng tin trong b&agrave;i:</p><p>Beginning immediately, staff members are asked to contribute toward the cost of hot beverages available in our kitchenette.</p>",
                "en": "<p>What is the purpose of the memo?</p><p>A. To explain a new policy</p><p>B. To discourage long breaks</p><p>C. To provide a budget update</p><p>D. To address staff complaints</p>###<p>Information:</p><p>Beginning immediately, staff members are asked to contribute toward the cost of hot beverages available in our kitchenette.</p>",
                "ja": "<p>メモの目的は何ですか？</p><p>A.新しいポリシーを説明するには</p><p>B.長い休憩を思いとどまらせるため</p><p>C.予算の更新を提供する</p><p>D.スタッフの苦情に対処するため</p>###<p>Information:</p><p>Beginning immediately, staff members are asked to contribute toward the cost of hot beverages available in our kitchenette.</p>",
                "vi": "<p>Mục đ&iacute;ch của thư b&aacute;o l&agrave; g&igrave;?</p><p>A. Để giải th&iacute;ch một ch&iacute;nh s&aacute;ch mới</p><p>B. Khuyến kh&iacute;ch kh&ocirc;ng nghỉ l&acirc;u d&agrave;i</p><p>C. Cung cấp bảng cập nhật ng&acirc;n s&aacute;ch</p><p>D. Giải quyết khiếu nại của nh&acirc;n vi&ecirc;n</p>###<p>Th&ocirc;ng tin trong b&agrave;i:</p><p>Beginning immediately, staff members are asked to contribute toward the cost of hot beverages available in our kitchenette.</p>",
                "ko": "<p>메모의 목적은 무엇입니까?</p><p>A. 새로운 정책을 설명하기 위해</p><p>B. 장기 휴식을 방지하기 위해</p><p>C. 예산 업데이트 제공</p><p>D. 직원 불만 처리</p>###<p>Information:</p><p>Beginning immediately, staff members are asked to contribute toward the cost of hot beverages available in our kitchenette.</p>",
                "zh-CN": "<p>备忘录的目的是什么？</p><p>A. 解释新政策</p><p>B. 不鼓励长时间休息</p><p>C. 提供预算更新</p><p>D. 处理员工投诉</p>###<p>Information:</p><p>Beginning immediately, staff members are asked to contribute toward the cost of hot beverages available in our kitchenette.</p>",
                "fr": "<p>Quel est le but du mémo ?</p><p>A. Pour expliquer une nouvelle politique</p><p>B. Pour décourager les longues pauses</p><p>C. Fournir une mise à jour du budget</p><p>D. Pour traiter les plaintes du personnel</p>###<p>Information:</p><p>Beginning immediately, staff members are asked to contribute toward the cost of hot beverages available in our kitchenette.</p>",
                "zh-TW": "<p>備忘錄的目的是什麼？</p><p>A. 解釋新政策</p><p>B. 不鼓勵長時間休息</p><p>C. 提供預算更新</p><p>D. 處理員工投訴</p>###<p>Information:</p><p>Beginning immediately, staff members are asked to contribute toward the cost of hot beverages available in our kitchenette.</p>"
            }
        },
        {
            "question": "What are staff members asked to do?",
            "answers": [
                "Bring their own beverages to work",
                "Leave payments in a container",
                "Submit requests for supplies",
                "Keep the kitchenette tidy"
            ],
            "correctAnswer": 1,
            "image": "",
            "explainAll": {
                "vn": "<p>C&aacute;c nh&acirc;n vi&ecirc;n được y&ecirc;u cầu l&agrave;m g&igrave;?</p><p>A. Tự mang đồ uống đến chỗ l&agrave;m</p><p>B. Để lại tiền v&agrave;o trong một c&aacute;i lon</p><p>C. Gửi y&ecirc;u cầu cung cấp vật tư</p><p>D. Giữ bếp mini gọn g&agrave;ng</p>###<p>Th&ocirc;ng tin trong b&agrave;i:</p><p>For each cup of coffee or tea, you pour for yourself, we are requesting that you leave fifty cents.</p><p>Each time you prepare a beverage, just place your payment in the marked can on the counter next to the sink.</p>",
                "en": "<p>What are staff members asked to do?</p><p>A. Bring their own beverages to work</p><p>B. Leave payments in a container</p><p>C. Submit requests for supplies</p><p>D. Keep the kitchenette tidy</p>###<p>Information:</p><p>For each cup of coffee or tea, you pour for yourself, we are requesting that you leave fifty cents.</p><p>Each time you prepare a beverage, just place your payment in the marked can on the counter next to the sink.</p>",
                "ja": "<p>スタッフは何をするように頼まれていますか？</p><p>A.自分の飲み物を持ってきてください</p><p>B.支払いをコンテナに残す</p><p>C.消耗品のリクエストを送信する</p><p>D.簡易キッチンを整頓する</p>###<p>Information:</p><p>For each cup of coffee or tea, you pour for yourself, we are requesting that you leave fifty cents.</p><p>Each time you prepare a beverage, just place your payment in the marked can on the counter next to the sink.</p>",
                "vi": "<p>C&aacute;c nh&acirc;n vi&ecirc;n được y&ecirc;u cầu l&agrave;m g&igrave;?</p><p>A. Tự mang đồ uống đến chỗ l&agrave;m</p><p>B. Để lại tiền v&agrave;o trong một c&aacute;i lon</p><p>C. Gửi y&ecirc;u cầu cung cấp vật tư</p><p>D. Giữ bếp mini gọn g&agrave;ng</p>###<p>Th&ocirc;ng tin trong b&agrave;i:</p><p>For each cup of coffee or tea, you pour for yourself, we are requesting that you leave fifty cents.</p><p>Each time you prepare a beverage, just place your payment in the marked can on the counter next to the sink.</p>",
                "ko": "<p>직원들이 무엇을 하라고 요청받았습니까?</p><p>A. 직장에 자신의 음료를 가져 오십시오.</p><p>B. 결제를 컨테이너에 보관</p><p>C. 공급 요청 제출</p><p>D. 간이 주방을 깔끔하게 유지</p>###<p>Information:</p><p>For each cup of coffee or tea, you pour for yourself, we are requesting that you leave fifty cents.</p><p>Each time you prepare a beverage, just place your payment in the marked can on the counter next to the sink.</p>",
                "zh-CN": "<p>要求工作人员做什么？</p><p>A. 自带饮料上班</p><p>B. 将付款留在容器中</p><p>C. 提交供应请求</p><p>D. 保持小厨房整洁</p>###<p>Information:</p><p>For each cup of coffee or tea, you pour for yourself, we are requesting that you leave fifty cents.</p><p>Each time you prepare a beverage, just place your payment in the marked can on the counter next to the sink.</p>",
                "fr": "<p>Que demande-t-on aux membres du personnel ?</p><p>A. Apporter ses propres boissons au travail</p><p>B. Laisser les paiements dans un conteneur</p><p>C. Soumettre des demandes de fournitures</p><p>D. Gardez la kitchenette bien rangée</p>###<p>Information:</p><p>For each cup of coffee or tea, you pour for yourself, we are requesting that you leave fifty cents.</p><p>Each time you prepare a beverage, just place your payment in the marked can on the counter next to the sink.</p>",
                "zh-TW": "<p>要求工作人員做什麼？</p><p>A. 自帶飲料上班</p><p>B. 將付款留在容器中</p><p>C. 提交供應請求</p><p>D. 保持小廚房整潔</p>###<p>Information:</p><p>For each cup of coffee or tea, you pour for yourself, we are requesting that you leave fifty cents.</p><p>Each time you prepare a beverage, just place your payment in the marked can on the counter next to the sink.</p>"
            }
        }
    ],
    "correct_answers": [
        0,
        1
    ],
    "count_question": 2,
    "title": "In this part you will read a selection of texts, such as magezine and newspaper articles, letters, and advertisements. Each text is followed by several questions. Select the best answer for each question and mark the letter (A), (B), (C) or (D) on your answer sheet.",
    "title_trans": {
        "vn": "Trong phần này bạn sẽ đọc một số đoạn văn, ví dụ như tạp chí hay các bài báo, lá thư, và mẩu quảng cáo. Theo sau mỗi đoạn văn là 1 số câu hỏi. Hãy chọn đáp án đúng nhất cho mỗi câu hỏi và đánh dấu đáp án A, B, C hoặc D vào phần trả lời của bạn.",
        "en": "In this part you will read a selection of texts, such as magezine and newspaper articles, letters, and advertisements. Each text is followed by several questions. Select the best answer for each question and mark the letter (A), (B), (C) or (D) on your answer sheet."
    },
    "scores": [
        1,
        1
    ]
}