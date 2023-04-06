const WebSocket = require('ws');
//let socket = new WebSocket("ws://127.0.0.1:8000?message=*LOGIN*");
//let socket2 = new WebSocket("ws://127.0.0.1:8000?Message=*col1,col2,col3,col4,col5*");
function runCode() {
    let socket = new WebSocket("ws://127.0.0.1:8888");

    socket.onopen = function(e) {
      console.log("[open] Connection established");
      console.log("Sending to server");
      //socket.send("REGISTER,test123,charlie,skinner,chuckskined@gmail.com,1,address,1111,123,10,11");
      //socket.send("LOGIN,chuckskined@gmail.com,test123");
      //socket.send("EDIT,test12345,charleys,o'briens,chuckskined@gmail.com,1,newaddress2,2222,122,9,10");
      //socket.send("CONFIRM,chuckskined@gmail.com,14300");
      //socket.send("ADDMOVIE,tt1234567,casting,genre, producer, duration, trailPic, trailVid, 3, 2");
      //socket.send("REQUESTFORGOTPW,chuckskined@gmail.com");
      //socket.send("SUBMITFORGOTPW,chuckskined@gmail.com,newPW123,14899");
      socket.send("LOGOUT");
      
    };
    
    socket.onmessage = function(event) {
      console.log(`[message] Data received from server: ${event.data}`);
      socket.close();
    };
    
    socket.onclose = function(event) {
      if (event.wasClean) {
        console.log(`[close] Connection closed cleanly, code=${event.code} reason=${event.reason}`);
      } else {
        // e.g. server process killed or network down
        // event.code is usually 1006 in this case
        console.log('[close] Connection died');
      }
    };
    
    socket.onerror = function(error) {
      console.log(`[error]`);
    };
}
runCode();