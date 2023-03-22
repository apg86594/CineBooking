const WebSocket = require('ws');
let socket = new WebSocket("ws://127.0.0.1:8000?message=*LOGIN*");
let socket2 = new WebSocket("ws://127.0.0.1:8000?Message=*col1,col2,col3,col4,col5*");
