import React from 'react';
import Container from '../components/layout/Container'

class About extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ req }) {
        const userAgent = req ? req.headers['user-agent'] : navigator.userAgent
        return { userAgent };
    }

    render() {
        return (
            <Container>
                <h1>About Page: {this.props.userAgent}</h1>
            </Container>
        );
    }
}

export default About