import React from 'react';
import { StyleSheet, View } from 'react-native';
import {
  heightPercentageToDP as hp,
  widthPercentageToDP as wp,
} from 'react-native-responsive-screen';

import CustomTextInfo from './CustomTextInfo';

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
  return (
    <View style={styles.container}>
      <CustomTextInfo infoTitle='Autor' infoValue={author} />
      <CustomTextInfo infoTitle='Tipo de autoria' infoValue={authorshipKind} />
    </View>
  );
};

export default AuthorshipCard;

