import java.io.File;
import java.io.IOException;

public class fooHttpServer {
	private static final int DEFAULT_PORT = 8080;
	private static final String DEFAULT_WEB_ROOT = "./www";

	public static void main(String[] args) {
		int port = DEFAULT_PORT;
		String webRoot = DEFAULT_WEB_ROOT;

		try {
			File dir = new File(DEFAULT_WEB_ROOT);

			// Create the root directory if not exists
			if (!dir.exists()) {
				if (dir.mkdirs()) {
					System.out.println("Directory '" + DEFAULT_WEB_ROOT + "' created successfully.");
				} else {
					System.out.println("Failed to create the directory '" + DEFAULT_WEB_ROOT + "'");
					System.exit(1);
				}
			}

			// Parse command line arguments
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
				case "-p":
				case "--port":
					if (i + 1 < args.length) {
						port = Integer.parseInt(args[++i]);
						if (port < 1 || port > 65535) {
							System.err.println("Error: Port must be between 1 and 65535");
							printUsage();
							System.exit(1);
						}
					} else {
						System.err.println("Error: Port number required after " + args[i]);
						printUsage();
						System.exit(1);
					}
					break;

				case "-r":
				case "--root":
					if (i + 1 < args.length) {
						webRoot = args[++i];
					} else {
						System.err.println("Error: Directory path required after " + args[i]);
						printUsage();
						System.exit(1);
					}
					break;

				case "-h":
				case "--help":
					printUsage();
					System.exit(0);
					break;

				default:
					System.err.println("Error: Unknown option: " + args[i]);
					printUsage();
					System.exit(1);
				}
			}
		} catch (NumberFormatException e) {
			System.err.println("Error: Invalid port number");
			printUsage();
			System.exit(1);
		}

		// Create and start the server
		SimpleHttpServer server = new SimpleHttpServer(port, webRoot);

		try {
			server.start();
		} catch (IOException e) {
			System.err.println("Failed to start server: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	private static void printUsage() {
		System.out.println("fooProjects http server - A simple HTTP server for educational purposes");
		System.out.println();
		System.out.println("Usage: java fooHttpServer [OPTIONS]");
		System.out.println();
		System.out.println("Options:");
		System.out.println("  -p, --port <number>     Port number to listen on (default: 8080)");
		System.out.println("  -r, --root <path>       Web root directory path (default: ./www)");
		System.out.println("  -h, --help              Display this help message");
		System.out.println();
		System.out.println("Examples:");
		System.out.println("  java fooHttpServer");
		System.out.println("  java fooHttpServer --port 3000");
		System.out.println("  java fooHttpServer --port 8000 --root /var/www/html");
		System.out.println("  java fooHttpServer -p 9000 -r ./public");
		System.out.println();
		System.out.println("Press Ctrl+C to stop the server");
	}
}
