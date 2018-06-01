# Bitcoin Wallet CLI
This is a command line interface for the bitcoin wallet.
All the keys are delt with in [Wallet Import Format (WIF)](https://en.bitcoin.it/wiki/Private_key#Base58_Wallet_Import_format)


## Available commands
* `generate`: generates a new bitcoin address with its private key
* `calculate`: calculates the bitcoin address for a private key
* `encrypt`: encrypts a private key using [BIP-38](https://github.com/bitcoin/bips/blob/master/bip-0038.mediawiki) 
* `decrypt`: decrypts an encrypted private key
* `exit`: exits the CLI


#### generate
generates a new bitcoin address with its encrypted private key, this command does not expect any option, but it will 
ask for a password to be used in encrypting the private key.

The private key will be encrypted using [BIP-38](https://github.com/bitcoin/bips/blob/master/bip-0038.mediawiki)

###### Options:
none

###### Example: 
`generate`


#### calculate
calculates the bitcoin address for a private key that is encrypted using [BIP-38](https://github.com/bitcoin/bips/blob/master/bip-0038.mediawiki)

###### Options:
* `key`: the encryted private key to calculate the bitcoin address from (in WIF)

###### Example:
`calculate -key 6PRKPUzHTcX4oy63Dqk5qYdF7gW3RRdU6TSdt4hAzx6j8G8cWRioir3xmV`



#### encrypt
encrypts a private key using [BIP-38](https://github.com/bitcoin/bips/blob/master/bip-0038.mediawiki)

###### Options:
* `key`: the encryted private key to decrypt (in WIF)

###### Example:
`encrypt -key 5KDqj5o743RPdgAakB3cqmhiWGvF6MQzaRjjtrMWzqxWh3tdYXt`



#### decrypt
decrypts a private key that is encrypted using [BIP-38](https://github.com/bitcoin/bips/blob/master/bip-0038.mediawiki)

###### Options:
* `key`: the encryted private key to decrypt (in WIF)

###### Example:
`decrypt -key 6PRKPUzHTcX4oy63Dqk5qYdF7gW3RRdU6TSdt4hAzx6j8G8cWRioir3xmV`


#### exit
exits the program

###### Options:
none

###### Example
`exit`


