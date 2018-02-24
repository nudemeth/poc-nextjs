import { createStore, applyMiddleware } from 'redux';
import withRedux from 'next-redux-wrapper';
import nextReduxSaga from 'next-redux-saga';
import createSagaMiddleware from 'redux-saga';
import mainReducer, { appInitialState } from './reducer';
import rootSaga from './saga';

const sagaMiddleware = createSagaMiddleware();

const bindMiddleware = (middleware) => {
    return applyMiddleware(...middleware);
}

export function configureStore (initialState = appInitialState) {
    const store = createStore(
        mainReducer,
        initialState,
        bindMiddleware([sagaMiddleware])
    );

    store.runSagaTask = () => {
        store.sagaTask = sagaMiddleware.run(rootSaga);
    };
    
    store.runSagaTask();
    return store;
}

export function withReduxSaga (...connectArgs) {
    return Page =>  withRedux(configureStore, ...connectArgs)(nextReduxSaga(Page));
}
