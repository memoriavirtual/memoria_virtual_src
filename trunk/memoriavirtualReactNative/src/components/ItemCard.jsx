import React from 'react';
import { StyleSheet, View, Text } from 'react-native';

import itemCardTypes from '../enums/itemCardTypes';
import itemCardStyles from '../styles/itemCard';

const styles = StyleSheet.create({
  ...itemCardStyles(),
});

const ItemCard = ({ type, title, value }) => {
  if (type === itemCardTypes.LARGE_TEXT) {
    return (
      <View style={styles.mainContainer}>
        <View style={styles.titleContainer}>
          <Text style={styles.titleText}>{title}</Text>
        </View>
        <View style={styles.valueContainer}>
          <Text style={styles.valueLargeText}>{value}</Text>
        </View>
      </View>
    );
  }

  return (
    <View style={styles.mainContainer}>
      <Text>EE</Text>
    </View>
  );
};

export default ItemCard;