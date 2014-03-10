#!/bin/bash
if ! which node
    then echo "I'm very, very sorry, but you'll need to install node"
fi

sudo npm install -g gulp

npm install

gulp
