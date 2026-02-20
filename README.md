**To compile:**<br/>
$ javac fooHttpServer.java SimpleHttpServer.java<br/>
<br/>
**To run:**<br/>
$ java fooHttpServer<br/>
<br/>
**Help:**<br/>
$ java fooHttpServer -h<br/><br/>
Simple Web Server - A lightweight HTTP server for serving static files<br/>
<br/>
Usage: java fooHttpServer [OPTIONS]<br/>
<br/>
Options:<br/>
  -p, --port <number>     Port number to listen on (default: 8080)<br/>
  -r, --root <path>       Web root directory path (default: ./www)<br/>
  -h, --help              Display this help message<br/>
<br/>
Examples:<br/>
  java fooHttpServer<br/>
  java fooHttpServer --port 3000<br/>
  java fooHttpServer --port 8000 --root /var/www/html<br/>
  java fooHttpServer -p 9000 -r ./public<br/>
<br/>
Press Ctrl+C to stop the server<br/>
