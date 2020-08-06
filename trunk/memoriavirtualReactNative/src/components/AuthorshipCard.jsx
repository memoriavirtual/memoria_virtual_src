import React, { useContext } from 'react';
import { StyleSheet, View } from 'react-native';
import {
  heightPercentageToDP as hp,
  widthPercentageToDP as wp,
} from 'react-native-responsive-screen';

import CustomTextInfo from './CustomTextInfo';

import { Context as I18nContext } from '../context/I18nContext';

const styles = StyleSheet.create({
  container: {
    borderColor: '#B4B4B4',
    borderWidth: 1,
    borderRadius: 10,
    paddingVertical: hp('1%'),
    width: wp('94%'),
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: hp('2%'),
  },
});

const AuthorshipCard = ({ author, authorshipKind }) => {
  const { state: { translate } } = useContext(I18nContext);

  return (
    <View style={styles.container}>
      <CustomTextInfo infoTitle={translate('AUTHORSHIP_AUTHOR')} infoValue={author} />
      <CustomTextInfo infoTitle={translate('AUTHORSHIP_TYPE')} infoValue={authorshipKind} />
    </View>
  );
};

export default AuthorshipCard;

