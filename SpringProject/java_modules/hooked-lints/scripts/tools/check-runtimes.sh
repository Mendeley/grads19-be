#!/usr/bin/env bash
NODE_VERSION=10.16.0
NVM_DIR="$HOME/.nvm"

# Install NVM if nvm.sh is not found
if [ ! -s "${NVM_DIR}/nvm.sh" ]; then
	curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.34.0/install.sh | bash
fi

# Install and use required Node.js language runtime
. "$NVM_DIR/nvm.sh"
nvm install "$NODE_VERSION"
nvm use "$NODE_VERSION"
