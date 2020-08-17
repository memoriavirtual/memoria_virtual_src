import React, { useContext } from 'react';
import { StyleSheet, ScrollView } from 'react-native';

import generalinfoStyles from '../styles/generalinfo';

import itemCardTypes from '../enums/itemCardTypes';

import Title from '../components/Title';
import ItemCard from '../components/ItemCard';

import { Context as I18nContext } from '../context/I18nContext';

const styles = StyleSheet.create({
  ...generalinfoStyles(),
});

const ReviewsScreen = () => {
  const { state: { translate } } = useContext(I18nContext);

  // Mock relatedGoods
  const relatedGoods = [
    {
      key: '1',
      value: 'Revisão 1 - mock',
    },

  ];

  return (
    <ScrollView contentContainerStyle={styles.generalPurposeForm}>
      <Title titleText="Sibipiruna" />
      {relatedGoods.map((item) => (
        <ItemCard
          type={itemCardTypes.LARGE_TEXT}
          title={translate('REVIEWS_REVIEW')}
          value={item.value}
        />
      ))}
    </ScrollView>
  );
};

ReviewsScreen.navigationOptions = () => ({
  headerTitle: 'Revisões',
});

export default ReviewsScreen;
