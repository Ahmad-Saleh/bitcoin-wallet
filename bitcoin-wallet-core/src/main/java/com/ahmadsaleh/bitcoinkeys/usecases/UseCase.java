package com.ahmadsaleh.bitcoinkeys.usecases;

@FunctionalInterface
public interface UseCase<R, S> {

    S exeute(R request);

}
