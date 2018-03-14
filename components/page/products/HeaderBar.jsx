import React from 'react';
import PropTypes from 'prop-types';

class HeaderBar extends React.Component {
    constructor(props) {
        super(props);
    }

    /*static propTypes = {
        classes: PropTypes.object.isRequired,
        theme: PropTypes.object.isRequired
    }*/

    render() {
        return (
            <span>This is AppBar</span>
        );
    }
}

export default HeaderBar