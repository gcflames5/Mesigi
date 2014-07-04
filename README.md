Mesigi
======

Mesigi aims to be a largely decentralized messaging alternative. The main reason for its development is so that I can delve deeper into the world of internet security as I aim to make this messenger as "unhackable" as my abilities allow. At its completion, a user will be able to register an account, log in, connect to any message server, view, add, and remove contacts, and send messages and files to those contacts. All messaging information is not kept by the server, and is instead (optionally) locally stored for each client. The server never has access to your password, but does have access to your sessionId (an id that acts as a temporary password to hide your real one), so if you were to accidently authenticate yourself on a malicious server, it would only be able to impersonate you until you log in again (since your sessionId is recreated upon logging in).


The Authentication Sytem
====

First, the client generates a  random secret-key 500 bytes long, which it then sends the to server. The server checks the validity of that key and protocol of the packet and, if all is well, sends back a AuthenticaionRequestPacket that holds the same secret key. The client recieves the packet and makes sure that the key is the same one it sent the server and then sends the username and sessionId of the user to the server in a AuthenticationPacket. The server checks the credentials with the main user server and tells the client whether or not the credentials were acceptable. If they are not, the server terminates the connetion immediatly.
