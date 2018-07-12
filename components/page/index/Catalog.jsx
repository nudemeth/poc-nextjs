import React from 'react';
import PropTypes from 'prop-types';
import GridList from '@material-ui/core/GridList';
import Hidden from '@material-ui/core/Hidden';
import uuidv4 from 'uuid/v4';
import { withStyles } from '@material-ui/core/styles';
import { connect } from 'react-redux';
import Item from './Item';

const styles = theme => ({
    gridList: {
        width: '100%',
        [theme.breakpoints.up('lg')]: {
            paddingLeft: '15%',
            paddingRight: '15%'
        }
    }
});

class Catalog extends React.Component {
    constructor(props) {
        super(props);
    }

    static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired,
        items: PropTypes.arrayOf(PropTypes.object).isRequired
    }

    render() {
        const { classes, items } = this.props;
        const list = items.map((item, index) => <Item key={item.id} item={item} />);
        return (
            <React.Fragment>
                <Hidden mdDown key={uuidv4()}>
                    <GridList cellHeight={180} cols={3} spacing={24} className={classes.gridList}>
                        {list}
                    </GridList>
                </Hidden>
                <Hidden xsDown lgUp key={uuidv4()}>
                    <GridList cellHeight={180} cols={2} spacing={16} className={classes.gridList}>
                        {list}
                    </GridList>
                </Hidden>
                <Hidden smUp key={uuidv4()}>
                    <GridList cellHeight={180} cols={1} className={classes.gridList}>
                        {list}
                    </GridList>
                </Hidden>
            </React.Fragment>
        );
    }
}

const mapStateToProps = ({ itemReducer: { items, error }}) => ({ items, error });

export default connect(mapStateToProps)(withStyles(styles, { withTheme: true })(Catalog));
export { Catalog };