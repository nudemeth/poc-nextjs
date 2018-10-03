import React from "react"
import PropTypes from "prop-types"
import GridList from "@material-ui/core/GridList"
import Hidden from "@material-ui/core/Hidden"
import uuidv4 from "uuid/v4"
import { withStyles } from "@material-ui/core/styles"
import { connect } from "react-redux"
import CatalogBrandItem from "./CatalogBrandItem"

const styles = theme => ({
    gridList: {
        width: "100%",
        [theme.breakpoints.up("lg")]: {
            paddingLeft: "15%",
            paddingRight: "15%"
        }
    }
})

class CatalogBrandList extends React.Component {
    constructor(props) {
        super(props)
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        catalogBrands: PropTypes.arrayOf(PropTypes.object).isRequired
    }

    render() {
        const { classes, catalogBrands } = this.props
        const items = catalogBrands.map((catalogBrand) => <CatalogBrandItem key={catalogBrand.id} catalogBrand={catalogBrand} />)
        return (
            <React.Fragment>
                <Hidden mdDown key={uuidv4()}>
                    <GridList className={classes.gridList}>
                        {items}
                    </GridList>
                </Hidden>
                <Hidden xsDown lgUp key={uuidv4()}>
                    <GridList className={classes.gridList}>
                        {items}
                    </GridList>
                </Hidden>
                <Hidden smUp key={uuidv4()}>
                    <GridList className={classes.gridList}>
                        {items}
                    </GridList>
                </Hidden>
            </React.Fragment>
        )
    }
}

const mapStateToProps = ({ catalogBrandReducer: { catalogBrands, error }}) => ({ catalogBrands, error })

export default connect(mapStateToProps)(withStyles(styles, { withTheme: true })(CatalogBrandList))
export { CatalogBrandList }