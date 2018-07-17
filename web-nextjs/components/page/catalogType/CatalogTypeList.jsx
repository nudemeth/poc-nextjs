import React from 'react';
import PropTypes from 'prop-types';
import GridList from '@material-ui/core/GridList';
import Hidden from '@material-ui/core/Hidden';
import uuidv4 from 'uuid/v4';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import CatalogTypeItem from './CatalogTypeItem';

const styles = theme => ({
    gridList: {
        width: '100%',
        [theme.breakpoints.up('lg')]: {
            paddingLeft: '15%',
            paddingRight: '15%'
        }
    }
});

class CatalogTypeList extends React.Component {
    constructor(props) {
        super(props);
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        catalogTypes: PropTypes.arrayOf(PropTypes.object).isRequired
    }

    render() {
        const { classes, catalogTypes } = this.props;
        const items = catalogTypes.map((catalogType, index) => <CatalogTypeItem key={catalogType.id} catalogType={catalogType} />);
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
        );
    }
}

const mapStateToProps = ({ catalogTypeReducer: { catalogTypes, error }}) => ({ catalogTypes, error });

export default connect(mapStateToProps)(withStyles(styles, { withTheme: true })(CatalogTypeList));
export { CatalogTypeList };