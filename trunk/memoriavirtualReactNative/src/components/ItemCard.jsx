import React, {
  useState,
  useEffect,
  useContext,
} from 'react';
import {
  StyleSheet,
  View,
  Text,
  Switch,
} from 'react-native';

import itemCardTypes from '../enums/itemCardTypes';
import itemCardStyles from '../styles/itemCard';

import { Context as I18nContext } from '../context/I18nContext';

const styles = StyleSheet.create({
  ...itemCardStyles(),
});

const ItemCard = ({ type, title, value }) => {
  // Contexts
  const { state: { translate} } = useContext(I18nContext);

  // States and reducers
  const [switcher, setSwitcher] = useState(false);

  // Functions
  const renderItemValue = () => {
    if (type === itemCardTypes.LARGE_TEXT) {
      return (
        <View style={styles.valueLargeTextContainer}>
          <Text style={styles.valueLargeText}>{value}</Text>
        </View>
      );
    }
  
    if (type === itemCardTypes.TEXT) {
      return (
        <View style={styles.valueTextContainer}>
          <Text style={styles.Text}>{value}</Text>
        </View>
      );
    }
  
    if (type === itemCardTypes.SWITCHER) {
      return (
        <View style={styles.valueSwitchContainer}>
          <Text>{switcher ? translate('YES') : translate('NO')}</Text>
          <Switch
            trackColor={{ false: '#767577', true: '#81b0ff' }}
            onValueChange={switcher}
            value={setSwitcher}
            disabled
          />
        </View>
      );
    }

    return null;
  };

  // Effects
  useEffect(() => {
    if (type === itemCardTypes.SWITCHER) {
      setSwitcher(value);
    }
  }, []);

  return (
    <View style={styles.mainContainer}>
      <View style={styles.titleContainer}>
        <Text style={styles.titleText}>{title}</Text>
      </View>
      {renderItemValue()}
    </View>
  );
};

export default ItemCard;