import React from 'react';
import fetch from 'isomorphic-unfetch';
import Container from '../components/layout/Container';

class Index extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ req }) {
        const res = await fetch('http://localhost:5000/api/v1/home');
        const data = await res.json();

        return { greeting: data.greeting };
    }

    render() {
        return (
            <Container title='Index'>
                <h1>This is Index Page: {this.props.greeting}</h1>
            </Container>
        );
    }
}

export default Index;