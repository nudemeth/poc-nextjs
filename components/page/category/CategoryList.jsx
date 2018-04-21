import React from 'react';
import PropTypes from 'prop-types';
import GridList from 'material-ui/GridList';
import Hidden from 'material-ui/Hidden';
import uuidv4 from 'uuid/v4';
import { withStyles } from 'material-ui/styles';
import { connect } from 'react-redux';
import { loadProducts } from '../../../actions';
import CategoryItem from './CategoryItem';

const styles = theme => ({
    gridList: {
        width: '100%',
        height: '100%',
        [theme.breakpoints.up('lg')]: {
            paddingLeft: '15%',
            paddingRight: '15%'
        }
    }
});

class CategoryList extends React.Component {
    constructor(props) {
        super(props);
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        categories: PropTypes.arrayOf(PropTypes.object).isRequired
    }

    render() {
        const { classes, categories } = this.props;
        const items = categories.map((category, index) => <CategoryItem key={category.id} category={category} />);
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

const mapStateToProps = ({ categories, error }) => ({ categories, error });

export default connect(mapStateToProps)(withStyles(styles, { withTheme: true })(CategoryList));
export { CategoryList };