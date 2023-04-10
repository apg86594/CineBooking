const WebSocket = require('ws');
//let socket = new WebSocket("ws://127.0.0.1:8000?message=*LOGIN*");
//let socket2 = new WebSocket("ws://127.0.0.1:8000?Message=*col1,col2,col3,col4,col5*");
function runCode() {
    let socket = new WebSocket("ws://127.0.0.1:8888");

    socket.onopen = function(e) {
      console.log("[open] Connection established");
      console.log("Sending to server");
      //socket.send("REGISTER,test123,charlie,skinner,chuckskined@gmail.com,2, billline1,billline2,billzip,billcity,billstate,shipline1,shipline2,shipzip,shipcity,shipstate,12345,123,01,02,0");
      //socket.send("LOGIN,chuckskined@gmail.com,test123");
      //socket.send("EDIT,charleys,o'briens,chuckskined@gmail.com,2,newbill1,newbil2,newbzip,newbcity,newbstate,news1,news2,newszip,newscity,newsstate,1111,000,5,7,0");
      //socket.send("CONFIRM,chuckskined@gmail.com,14300");
      //socket.send("ADDMOVIE,tt1234567,casting,genre, producer, duration, trailPic, trailVid, 3, 2");
      //socket.send("REQUESTFORGOTPW,chuckskined@gmail.com");
      //socket.send("SUBMITFORGOTPW,chuckskined@gmail.com,newPW123,14899");
      //socket.send("LOGOUT");
      //socket.send("SEARCHGENRE,genre");
      //socket.send("SEARCHINPUT,tt123");
      //socket.send("GETMOVIES");
      //socket.send("GETSHOWTIMES,1");
      //socket.send("ADDPROMOTION,take15,15");
      //socket.send("GETPROMOTION");
      //socket.send("SENDPROMOTION,take15,15");
      //socket.send("SCHEDULEMOVIE, 1,1,50,2023-04-06 05:30:00.0")
      //socket.send("DELETEMOVIE,1");
      socket.send("EDITMOVIE,testtitle,testcasting,action,gary coleman,gary coleman,1h55m,gary coleman eats a pig,NOW PLAYING,testpic,testvideo,1,1,1");
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