package com.ahmadsaleh.bitcoinkeys.usecases;

@FunctionalInterface
public interface UseCase<R, S> {

    S execute(R request);

}
