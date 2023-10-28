#!/bin/bash

# Generate a new private key:
openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048

# Extract the public key from the private key:
openssl rsa -pubout -in private_key.pem -out public_key.pem

# Convert RSA public key to X509 PEM format:
openssl rsa -pubin -in public_key.pem -RSAPublicKey_out -out public_key_x509.pem
