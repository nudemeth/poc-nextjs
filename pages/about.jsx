import React from 'react';
import fetch from 'isomorphic-unfetch';
import Container from '../components/layout/Container';
import { withReduxSaga } from '../store';

class About extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ req }) {
        //const res = await fetch('http://localhost:5000/api/v1/about');
        //const data = await res.json();

        return { text: "About Page" };
    }

    render() {
        return (
            <Container title='About'>
                <h1>About Page: {this.props.text}</h1>
            </Container>
        );
    }
}

export default withReduxSaga()(About);
export { About };