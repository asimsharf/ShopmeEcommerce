## Step 2: Understand the Socket API

The Socket API provides a set of functions and data structures for creating, configuring, and using network sockets in C programs. Sockets are the endpoints for communication in a network, enabling processes to send and receive data over a network.

### Key Concepts and Functions

1. **Socket Creation (`socket()`)**:
   - The `socket()` function creates a new socket.
   - Syntax: `int socket(int domain, int type, int protocol);`
   - Parameters:
     - `domain`: Specifies the communication domain or address family (e.g., `AF_INET` for IPv4, `AF_INET6` for IPv6).
     - `type`: Specifies the socket type (e.g., `SOCK_STREAM` for TCP sockets, `SOCK_DGRAM` for UDP sockets).
     - `protocol`: Specifies the protocol to be used with the socket. In most cases, this can be set to `0` to choose the default protocol for the given domain and type.
   - Returns: On success, returns a file descriptor (a small non-negative integer). On failure, returns `-1`.

2. **Binding a Socket to an Address (`bind()`)**:
   - The `bind()` function associates a socket with a specific address (IP address and port number).
   - Syntax: `int bind(int sockfd, const struct sockaddr *addr, socklen_t addrlen);`
   - Parameters:
     - `sockfd`: The file descriptor of the socket to be bound.
     - `addr`: A pointer to a `sockaddr` structure containing the address information.
     - `addrlen`: The size of the address structure.
   - Returns: Returns `0` on success, `-1` on failure.

3. **Listening for Connections (`listen()`)**:
   - For TCP sockets, the `listen()` function prepares the socket to accept incoming connections.
   - Syntax: `int listen(int sockfd, int backlog);`
   - Parameters:
     - `sockfd`: The file descriptor of the socket to listen on.
     - `backlog`: The maximum length of the queue of pending connections.
   - Returns: Returns `0` on success, `-1` on failure.

4. **Accepting Connections (`accept()`)**:
   - The `accept()` function blocks until a client connects to the server, then creates a new socket for the client connection.
   - Syntax: `int accept(int sockfd, struct sockaddr *addr, socklen_t *addrlen);`
   - Parameters:
     - `sockfd`: The file descriptor of the listening socket.
     - `addr`: A pointer to a `sockaddr` structure to store the address of the client.
     - `addrlen`: A pointer to the size of the `addr` structure.
   - Returns: Returns a new file descriptor for the accepted connection. On failure, returns `-1`.

5. **Connecting to a Server (`connect()`)**:
   - For TCP client sockets, the `connect()` function establishes a connection to a remote server.
   - Syntax: `int connect(int sockfd, const struct sockaddr *addr, socklen_t addrlen);`
   - Parameters:
     - `sockfd`: The file descriptor of the socket.
     - `addr`: A pointer to a `sockaddr` structure containing the address of the server.
     - `addrlen`: The size of the address structure.
   - Returns: Returns `0` on success, `-1` on failure.

These are some of the fundamental functions used in socket programming. To fully understand socket programming in C, study these functions in detail, understand their parameters, return values, and error handling mechanisms. Additionally, learn about socket options, data transmission, error handling, and network programming best practices.
