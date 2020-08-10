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
  TouchableOpacity,
} from 'react-native';

import itemCardTypes from '../enums/itemCardTypes';
import itemCardStyles from '../styles/itemCard';

import { Context as I18nContext } from '../context/I18nContext';

const styles = StyleSheet.create({
  ...itemCardStyles(),
});

const ItemCard = ({ type, title, value, researchItem }) => {
  // Contexts
  const { state: { translate } } = useContext(I18nContext);

  // States and reducers
  const [switcher, setSwitcher] = useState(false);

  // Functions
  const renderLargeText = () => (
    <View style={styles.valueLargeTextContainer}>
      <Text style={styles.valueLargeText}>{value}</Text>
    </View>
  );

  const renderText = () => (
    <View style={styles.valueTextContainer}>
      <Text style={styles.valueText}>{value}</Text>
    </View>
  );

  const renderSwitcher = () => (
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

  const renderResearcher = () => (
    <View style={styles.itemResearcherContainer}>
      <View style={styles.itemResearcherLineContainer}>
        <Text style={styles.itemResearcherTopicText}>{translate('DATE')}: </Text>
        <Text>{researchItem.date}</Text>
      </View>
      <View style={styles.itemResearcherLineContainer}>
        <Text style={styles.itemResearcherTopicText}>{translate('NAME')}: </Text>
        <Text>{researchItem.researcher}</Text>
      </View>
      <View style={styles.itemResearcherLineContainer}>
        <Text style={styles.itemResearcherTopicText}>{translate('NOTES')}: </Text>
        <Text>{researchItem.notes}</Text>
      </View>
    </View>
  );

  const renderRelatedGood = () => (
    <TouchableOpacity>
      <View style={styles.relatedGoodsContainer}>
        <Text style={styles.relatedGoodTouchableText}>{value}</Text>
      </View>
    </TouchableOpacity>
  );

  const renderItemValue = () => {
    if (type === itemCardTypes.LARGE_TEXT) {
      return renderLargeText();
    }
  
    if (type === itemCardTypes.TEXT) {
      return renderText();
    }
  
    if (type === itemCardTypes.SWITCHER) {
      return renderSwitcher();
    }

    if (type === itemCardTypes.RESEARCHER) {
      return renderResearcher();
    }

    if (type === itemCardTypes.RELATED_GOOD) {
      return renderRelatedGood();
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