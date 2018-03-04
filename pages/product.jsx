import React from 'react';
import Container from '../components/layout/Container';
import { withReduxSaga } from '../store';

class Product extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ req }) {
        return {};
    }

    render() {
        return (
            <Container title='Product'>
                <h1>Product Page</h1>
            </Container>
        );
    }
}

export default withReduxSaga()(Product);
export { Product };