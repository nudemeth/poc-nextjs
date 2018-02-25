import React from 'react';
import Container from '../components/layout/Container';
import LoginCard from '../components/page/login/LoginCard';
import { withReduxSaga } from '../store';

class Login extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Container title='Login'>
                <LoginCard />
            </Container>
        );
    }
}

export default withReduxSaga()(Login);
export { Login };