# Bitcoin Wallet 

Supports the following:
* generating a new bitcoin addresses
* encrypting private keys using [BIP-38](https://github.com/bitcoin/bips/blob/master/bip-0038.mediawiki)
* decrypting private keys encrypted using [BIP-38](https://github.com/bitcoin/bips/blob/master/bip-0038.mediawiki)
* calculating the bitcoin public address from a private key


### CLI

run the main class `com.ahmadsaleh.bitcoinkeys.console.Main`

```bash
  ___   _   _                _           __      __         _   _         _   
 | _ ) (_) | |_   __   ___  (_)  _ _     \ \    / /  __ _  | | | |  ___  | |_ 
 | _ \ | | |  _| / _| / _ \ | | | ' \     \ \/\/ /  / _` | | | | | / -_) |  _|
 |___/ |_|  \__| \__| \___/ |_| |_||_|     \_/\_/   \__,_| |_| |_| \___|  \__|


Available commands:
* exit: Exit the application.
* encrypt -key [private key]: encrypts a bitcoin private key using BIP-38.
		example: encrypt -key 5J3SDGJty2TfAm6yKe8vPAPwF7EVsG4SpZMUNbtPhg4mTdAAaDP
* calculate -key [encrypted private key]: calculates the bitcoin public key from an encrypted private key (BIP-38).
		example: calculate -key 6PRSTTzmeteWVaaUq4368sz5NqLJdA7HUsps335aJKCPdoWb4sBdAweTA9
* decrypt -key [encrypted private key]: decrypts a private key that is encrypted using BIP-38.
		example: decrypt -key 6PRSTTzmeteWVaaUq4368sz5NqLJdA7HUsps335aJKCPdoWb4sBdAweTA9
* generate: generates a bitcoin key pair.
```
