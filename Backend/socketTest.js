const WebSocket = require('ws');
//let socket = new WebSocket("ws://127.0.0.1:8000?message=*LOGIN*");
//let socket2 = new WebSocket("ws://127.0.0.1:8000?Message=*col1,col2,col3,col4,col5*");
function runCode() {
    let socket = new WebSocket("ws://127.0.0.1:8888");

    socket.onopen = function(e) {
      console.log("[open] Connection established");
      console.log("Sending to server");
      socket.send("My name is John");
    };
    
    socket.onmessage = function(event) {
      console.log(`[message] Data received from server: ${event.data}`);
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