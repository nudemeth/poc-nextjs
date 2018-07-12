import React from 'react';
import Container from '../components/layout/Container';
import Catalog from '../components/page/index/Catalog';
import HeaderContent from '../components/page/index/HeaderContent';
import { loadItems } from '../actions/catalog.actions';
import { withReduxSaga } from '../store/store';

class Index extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ store }) {
        store.dispatch(loadItems());
        return { text: "Index Page" };
    }

    componentDidMount() {
        this.props.dispatch(loadItems());
    }

    render() {
        return (
            <Container title='Index' header={<HeaderContent/>}>
                <Catalog/>
            </Container>
        );
    }
}

export default withReduxSaga()(Index);
export { Index };