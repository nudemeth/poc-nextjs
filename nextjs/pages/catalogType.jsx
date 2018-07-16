import React from 'react';
import Container from '../components/layout/Container';
import { loadCatalogTypes } from '../actions/catalogType.actions';
import { withReduxSaga } from '../store/store';
import CatalogTypeList from '../components/page/catalogType/CatalogTypeList';

class CatalogType extends React.Component {
    constructor(props) {
        super(props);
    }

    static async getInitialProps({ store }) {
        store.dispatch(loadCatalogTypes());
    }

    componentDidMount() {
        this.props.dispatch(loadCatalogTypes());
    }

    render() {
        return (
            <Container title='Catalog Type'>
                <CatalogTypeList/>
            </Container>
        );
    }
}

export default withReduxSaga()(CatalogType);
export { CatalogType };