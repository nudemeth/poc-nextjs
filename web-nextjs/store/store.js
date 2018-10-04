import { createStore, applyMiddleware } from 'redux'
import withRedux from 'next-redux-wrapper'
import nextReduxSaga from 'next-redux-saga'
import createSagaMiddleware from 'redux-saga'
import rootReducer from '../reducers/root.reducer'
import rootSaga from '../sagas/root.saga'

const sagaMiddleware = createSagaMiddleware()

const bindMiddleware = (middleware) => {
    return applyMiddleware(...middleware)
}

export function configureStore (initialState) {
    const store = createStore(
        rootReducer,
        initialState,
        bindMiddleware([sagaMiddleware])
    )

    store.runSagaTask = () => {
        store.sagaTask = sagaMiddleware.run(rootSaga)
    }
    
    store.runSagaTask()
    return store
}

export function withReduxSaga (...connectArgs) {
    return Page => withRedux(configureStore, ...connectArgs)(nextReduxSaga(Page))
}
