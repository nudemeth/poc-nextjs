import React from 'react';
import Container from '../components/layout/Container'

class Index extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ req }) {
        return { abc: "def" };
    }

    render() {
        return (
        <Container title='Index'>
            <h1>This is Index Page: {this.props.abc}</h1>
        </Container>
        );
    }
}

export default Index