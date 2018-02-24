import React from 'react';
import Container from '../components/layout/Container';

class Login extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Container title='Login'>
                <h1>This is Index Login Page</h1>
            </Container>
        );
    }
}

export default Login;;
export { Login };