## A lightweight HTTP server for educational purposes. ##

**Compile:**
> **$** javac fooHttpServer.java SimpleHttpServer.java

**Run:** 
> **$** java fooHttpServer

**Help:**
> **$** java fooHttpServer -h <br/>
> Simple Http Server - A lightweight HTTP server for educational purposes
> 
> Usage: java fooHttpServer [OPTIONS] <br/>
> 
> Options: <br/>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-p, --port <number>     Port number to listen on (default: 8080) <br/>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-r, --root <path>       Web root directory path (default: ./www) <br/>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-h, --help              Display this help message <br/>
> 
> Examples: <br/>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;java fooHttpServer <br/>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;java fooHttpServer --port 3000 <br/>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;java fooHttpServer --port 8000 --root /var/www/html <br/>
> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;java fooHttpServer -p 9000 -r ./public <br/>
> 
> Press Ctrl+C to stop the server
