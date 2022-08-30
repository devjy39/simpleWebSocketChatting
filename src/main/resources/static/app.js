var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    //$("#greetings").html("");
}

function connect() {
    /*  sockJs, stomp.js lib를 사용하여 커넥션 url에 연결
    *   연결되는순간 클라이언트는 서버가 보낼 /topic/greetings 를 구독한 상태가 되고, 등록된 콜백함수 동작
    *   메시지를 받으면 DOM을 조작하여 메시지를 출력한다.
    * */
    var socket = new SockJS('http://localhost:8080/gs-guide-websocket'); //연결할 때만 풀 주소
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);



        stompClient.subscribe('/topic/greetings', function (chat) {
            const parse = JSON.parse(chat.body);
            showGreeting(parse.name, parse.message, parse.time);
        });

    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendTalk() {
    /*  서버의 STOMP 수신 URL 에 전송하는 함수
    * */
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val(), 'message': $("#message").val()}));
}

function showGreeting(name, message, time) {
    $("#greetings").append("<tr><td>" + name + " : " + message + "   ["+time+"]"+ "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendTalk(); });
});