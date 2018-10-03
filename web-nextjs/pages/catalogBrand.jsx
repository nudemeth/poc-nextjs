import React from "react"
import PropTypes from "prop-types"
import Container from "../components/layout/Container"
import { loadCatalogBrands } from "../actions/catalogBrand.actions"
import { withReduxSaga } from "../store/store"
import CatalogBrandList from "../components/page/catalogBrand/CatalogBrandList"

class CatalogBrand extends React.Component {
    constructor(props) {
        super(props)
    }

    static async getInitialProps({ store }) {
        store.dispatch(loadCatalogBrands())
    }

    static propTypes = {
        dispatch: PropTypes.func.isRequired
    }

    componentDidMount() {
        this.props.dispatch(loadCatalogBrands())
    }

    render() {
        return (
            <Container title="Catalog Brand">
                <CatalogBrandList/>
            </Container>
        )
    }
}

export default withReduxSaga()(CatalogBrand)
export { CatalogBrand }