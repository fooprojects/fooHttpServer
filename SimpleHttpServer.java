import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class SimpleHttpServer {
	private final int port;
	private final String webRoot;
	private ServerSocket serverSocket;

	// MIME types mapping
	private static final Map<String, String> MIME_TYPES = new HashMap<>();
	static {
		MIME_TYPES.put(".html", "text/html");
		MIME_TYPES.put(".htm", "text/html");
		MIME_TYPES.put(".css", "text/css");
		MIME_TYPES.put(".js", "text/javascript");
		MIME_TYPES.put(".jpg", "image/jpeg");
		MIME_TYPES.put(".jpeg", "image/jpeg");
		MIME_TYPES.put(".png", "image/png");
		MIME_TYPES.put(".gif", "image/gif");
		MIME_TYPES.put(".ico", "image/x-icon");
		//MIME_TYPES.put(".svg", "image/svg+xml");
		//MIME_TYPES.put(".txt", "text/text");
	}

	// Creates a new SimpleWebServer instance
	public SimpleHttpServer(int port, String webRoot) {
		this.port = port;
		this.webRoot = webRoot;
	}

	// Starts the web server
	public void start() throws IOException {
		File webRootDir = new File(webRoot);
		try {
			serverSocket = new ServerSocket(port);

			System.out.println("Server started on port " + port);
			System.out.println("Serving files from: " + webRootDir.getAbsolutePath());
			System.out.println("Visit: http://localhost:" + port);
			System.out.println("Press Ctrl+C to stop the server");

			while (true) {
				Socket clientSocket = serverSocket.accept();
				handleClient(clientSocket);
			}
		} catch (SocketException e) {
			System.err.println("Server error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void handleClient(Socket clientSocket) {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				OutputStream out = clientSocket.getOutputStream()) {

			// Read the request line
			String requestLine = in.readLine();
			if (requestLine == null || requestLine.isEmpty()) {
				return;
			}

			System.out.println("Request: " + requestLine);

			// Parse the request
			String[] parts = requestLine.split(" ");
			if (parts.length < 2) {
				sendResponse(out, 400, "text/plain", "Bad Request".getBytes());
				return;
			}

			String method = parts[0];
			String path = parts[1];

			// Only handle GET requests
			if (!method.equals("GET")) {
				sendResponse(out, 405, "text/plain", "Method Not Allowed".getBytes());
				return;
			}

			// Default to index.html if root path
			if (path.equals("/")) {
				path = "/index.html";
			}

			// Construct file path
			File file = new File(webRoot + path);

			// Security check: prevent directory traversal
			if (!file.getCanonicalPath().startsWith(new File(webRoot).getCanonicalPath())) {
				sendResponse(out, 403, "text/plain", "Forbidden".getBytes());
				return;
			}

			// Check if file exists
			if (!file.exists() || file.isDirectory()) {
				String notFoundHtml = "<html><body><h1>404 - File Not Found</h1><p>" + path + "</p></body></html>";
				sendResponse(out, 404, "text/html", notFoundHtml.getBytes());
				return;
			}

			// Read file content
			byte[] fileContent = Files.readAllBytes(file.toPath());

			// Get MIME type
			String mimeType = getMimeType(file.getName());

			// Send successful response
			sendResponse(out, 200, mimeType, fileContent);

		} catch (IOException e) {
			System.err.println("Error handling client: " + e.getMessage());
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				// Ignore
			}
		}
	}

	private void sendResponse(OutputStream out, int statusCode, String contentType, byte[] content) throws IOException {
		String statusText = getStatusText(statusCode);

		// Build HTTP response
		StringBuilder response = new StringBuilder();
		response.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		response.append("Content-Type: " + contentType + "\r\n");
		response.append("Content-Length: " + content.length + "\r\n");
		response.append("Connection: close\r\n");
		response.append("\r\n");

		// Send headers
		out.write(response.toString().getBytes());

		// Send body
		out.write(content);
		out.flush();
	}

	private String getMimeType(String filename) {
		int dotIndex = filename.lastIndexOf('.');
		if (dotIndex > 0) {
			String extension = filename.substring(dotIndex).toLowerCase();
			return MIME_TYPES.getOrDefault(extension, "application/octet-stream");
		}
		return "application/octet-stream";
	}

	private String getStatusText(int statusCode) {
		switch (statusCode) {
		case 200:
			return "OK";
		case 400:
			return "Bad Request";
		case 403:
			return "Forbidden";
		case 404:
			return "Not Found";
		case 405:
			return "Method Not Allowed";
		case 500:
			return "Internal Server Error";
		default:
			return "Unknown";
		}
	}
}